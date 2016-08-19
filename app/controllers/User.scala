package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{Action, Controller}


@Singleton
class User @Inject() extends Controller {

  def login = Action { implicit request => Ok(views.html.login()) }

  def profile = Action { implicit request => Ok(views.html.profile()) }
}
