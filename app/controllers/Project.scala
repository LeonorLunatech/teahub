package controllers

import javax.inject.{Inject, Singleton}

import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}

@Singleton
class Project @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {
//class Project @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {
  val fooForm = Form(single("foo" -> text(maxLength = 20)))
//
//  def projects = Action { implicit request => Ok(views.html.init_projects(fooForm))}

  def list = Action { implicit request => Ok(views.html.init_projects()) }
  def setup = Action { implicit request => Ok(views.html.init_setup_projects(fooForm)) }
  def details = Action { implicit request => Ok(views.html.init_project_details()) }
  def newProject = Action { implicit request => Ok(views.html.init_new_project()) }
  def usersProject = Action { implicit request => Ok(views.html.init_user_project()) }
  def issues = Action { implicit request => Ok(views.html.init_issues()) }

}
