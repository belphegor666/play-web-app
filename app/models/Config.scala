package models

import play.api.libs.json._

case class Config(name: String, value: String)

object Config {
  
  implicit val configFormat = Json.format[Config]
}