package controllers

import java.util.concurrent.TimeUnit
import javax.inject.Inject

import play.api.cache.CacheApi
import play.api.mvc._
import services.impl.ApiOAuthGitHubService

import scala.concurrent.duration.Duration
import scala.concurrent.{ExecutionContext, Future}

/**
  * This is the controller responsible for the actions related to OAuth authentication between TEAHub and GitHub.
  * @param oauthGitHubService helper service for authentication
  * @param executionContext the execution context for asynchronous execution of program logic
  */
class OAuthGitHubController @Inject()(oauthGitHubService: ApiOAuthGitHubService, cache: CacheApi)
                           (implicit executionContext: ExecutionContext) extends Controller {

  /**
    * Make the initial request to authenticate via GitHub with OAuth protocol.
    * @return Renders the login page and creates a session with the `oauth-state` in it.
    */
  def login = Action.async { implicit request =>
    val (connectURL, state) = oauthGitHubService.oauthGitHubConnectUrl
    Future.successful(Ok(views.html.index("Log-in to TEAHub", connectURL)).
      withSession("oauth-state" -> state)
    )
  }

  /**
    * The URL in our application where users will be sent after authorization will trigger this action. In the URL the
    * code and state parameters are filled in by GitHub.
    * @param codeOpt is the code received when requesting access to GitHub.
    * @param stateOpt an unguessable random string. It is used to protect against cross-site request forgery attacks.
    * @return The list of GitHub repositories if the authentication was successful.
    */
  def callback(codeOpt: Option[String] = None, stateOpt: Option[String] = None) = Action.async { implicit request =>
    (for {
      code <- codeOpt
      state <- stateOpt
      oauthState <- request.session.get("oauth-state")
    } yield {
      if (state == oauthState) {
        oauthGitHubService.getToken(code).map { accessToken =>
          Redirect(routes.OAuthGitHubController.success()).withSession("oauth-token" -> accessToken)
        }.recover {
          case ex: IllegalStateException => Unauthorized(ex.getMessage)
        }
      }
      else {
        Future.successful(BadRequest("Invalid github login"))
      }
    }).getOrElse(Future.successful(Redirect(controllers.routes.OAuthGitHubController.login))) // when using back button from /main to /callback we are sent to login page
  }

  /**
    * If authentication was successful returns the projects in GitHub.
    * @return A json object containing the list of GitHub repositories.
    */
  def success() = Action.async { request =>
    request.session.get("oauth-token").fold(Future.successful(Unauthorized("No way Jose"))) { authToken =>
      cache.set("authToken", authToken, Duration(60, TimeUnit.SECONDS))
      Future(Redirect(routes.TEAHubController.githubRepositories()))
    }
  }
}
