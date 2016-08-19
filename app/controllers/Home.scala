package controllers

import javax.inject.{Inject, Singleton}

//import play.api.i18n.{ MessagesApi, I18nSupport }

//import play.api.data.Form
import play.api.mvc.{Action, Controller}

//import play.api.data.Forms._
//import play.api.data.validation.Constraints._

@Singleton
class Home @Inject() extends Controller {
//class Home @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {
//  val fooForm = Form(single("foo" -> text(maxLength = 20)))
//
//  def index = Action { implicit request => Ok(views.html.index(fooForm)) }
//
//  def login = Action { implicit request => Ok(views.html.login()) }

  def index = Action { implicit request => Ok(views.html.index()) }
  def management = Action { implicit request => Ok(views.html.user_management()) }

}
