package controllers

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json
import models._

import play.api.libs.ws._
import play.api.Play.current

import scala.concurrent.{ ExecutionContext, Future }

import javax.inject._

import java.net.InetAddress

class ApplicationController @Inject() (implicit ec: ExecutionContext)
                                extends Controller {

  /**
   * The index action.
   */
  def index = Action {
    Ok(views.html.index())
  }

  /**
   * A REST endpoint that gets local hostname as JSON.
   */
  def getConfig = Action {
      
    val localhost = InetAddress.getByName("web-tmr")
    val hostname = localhost.getCanonicalHostName()
      
    Ok(Json.toJson(hostname))

  }
  
  /**
   * A REST endpoint that gets remote config from a Web Service as JSON.
   */
  def getRemoteConfig = Action.async {
 
    val localhost = InetAddress.getByName("localhost.localdomain")
    val hostname = "api-tmr.apps.eu01.cf.canopy-cloud.com"//localhost.getCanonicalHostName()
      
    val wsUrl = "http://"+hostname+"/config"
    WS.url(wsUrl).get().map { response =>
        Ok("Result: " + response.body)
    }
  }  
  
}

/**
 * The create person form.
 *
 * Generally for forms, you should define separate objects to your models, since forms very often need to present data
 * in a different way to your models.  In this case, it doesn't make sense to have an id parameter in the form, since
 * that is generated once it's created.
 */
case class CreatePersonForm(name: String, age: Int)
