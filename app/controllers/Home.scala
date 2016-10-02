package controllers

import javax.inject.{Inject, Singleton}

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}

@Singleton
class Home @Inject() (val messagesApi: MessagesApi) extends Controller with I18nSupport {
//  val fooForm = Form(single("foo" -> text(maxLength = 20)))
//
//  def index = Action { implicit request => Ok(views.html.index(fooForm)) }
//
//  def login = Action { implicit request => Ok(views.html.login()) }

  def index = Action { implicit request => Ok(views.html.init_index()) }
  def management = Action { implicit request => Ok(views.html.init_user_management()) }

}
