package controllers

import javax.inject.{Inject, Singleton}

import models.NewProject
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.JsArray
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import play.api.mvc.{Action, Controller}

import scala.concurrent.{ExecutionContext, Future}


@Singleton
/**
  * Controller for front end
  */
class UIController @Inject()(ws: WSClient, val messagesApi: MessagesApi)(implicit executionContext: ExecutionContext) extends Controller with I18nSupport {
  val togglTokenForm = Form(single("togglToken" -> text(maxLength = 20)))

  def index = Action { implicit request => Ok(views.html.index("Welcome to TeaHub", "")) }
  def management = Action { implicit request => Ok(views.html.user_management()) }
  def list = Action { implicit request => Ok(views.html.projects()) }
  def setup = Action { implicit request => Ok(views.html.setup_projects(togglTokenForm)) }
  def details = Action { implicit request => Ok(views.html.project_details()) }
  def issues = Action { implicit request => Ok(views.html.issues()) }
  def profile = Action { implicit request => Ok(views.html.profile()) }

  def getAllGitHubProjects(username: String) = Action.async { implicit request =>
    //https://developer.github.com/v3/repos/
    val url = "https://api.github.com/users/LeonorLunatech/repos"
    val request: WSRequest = ws.url(url)

    val futureResponse: Future[WSResponse] = request.get()

    val projects = futureResponse.map {
      response => {
        val projects = response.json.as[JsArray].value
        for (proj <- projects)
          yield (proj \ "svn_url").as[String]
      }
    }

    val map = projects.map(projs => projs.map(p => p -> p))

    projects.map(p => Ok(p.toString))
  }

  def getAllToggleProjects(username: String) = Action.async { implicit request =>
    Future.successful(Ok("ToggleProject1, ToggleProject2 "))
  }

  val newProjectForm = Form(
    mapping(
      "githubProject" -> nonEmptyText,
      "toggleProject" -> nonEmptyText,
      "projectName" -> nonEmptyText
    )(NewProject.apply)(NewProject.unapply))
}
