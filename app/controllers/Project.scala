package controllers

import javax.inject.{Inject, Singleton}

//import play.api.i18n.{ MessagesApi, I18nSupport }
//import play.api.data.Form
import play.api.mvc.{Action, Controller}

import play.api.data.Forms._

@Singleton
class Project @Inject() extends Controller {
//class Project @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {
//  val fooForm = Form(single("foo" -> text(maxLength = 20)))
//
//  def projects = Action { implicit request => Ok(views.html.init_projects(fooForm))}

  def list = Action { implicit request => Ok(views.html.projects()) }
  def details = Action { implicit request => Ok(views.html.project_details()) }
  def newProject = Action { implicit request => Ok(views.html.new_project()) }
  def usersProject = Action { implicit request => Ok(views.html.user_project()) }
  def issues = Action { implicit request => Ok(views.html.issues()) }

}
