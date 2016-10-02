package controllers

import javax.inject.{Inject, Singleton}

import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}


@Singleton
class User @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport{

  def login = Action { implicit request => Ok(views.html.login()) }

  def profile = Action { implicit request => Ok(views.html.init_profile()) }
}
