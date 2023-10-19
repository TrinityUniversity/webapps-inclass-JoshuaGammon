package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer

@Singleton
class Application @Inject()(cc: ControllerComponents)(implicit system: ActorSystem, mat: Materializer) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def newPage = Action {
    Ok(views.html.newPage(SharedMessages.itWorks))
  }

  def newTable = Action {
    Ok(views.html.newTable(SharedMessages.itWorks))
  }

  def colorSubmit = Action {
    Ok(views.html.colorSubmit(SharedMessages.itWorks))
  }

  def enterUser = Action {
    Ok(views.html.enterUser())
  }

  def enterUserController = Action { request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args{"username"}.head
      Ok(s"$username logged in")
    }.getOrElse(Ok("Oops"))
  }
  
  def numberClicker = Action { implicit request =>
    Ok(views.html.numberClicker())
  }

  def getNumber = Action {
    Ok(models.CountingModel.click().toString)
  }

  def getSocket = WebSocket.accept[String, String] { request =>
    println("Getting socket")
    ActorFlow.actorRef { out =>
      ???
    }
  }
}
