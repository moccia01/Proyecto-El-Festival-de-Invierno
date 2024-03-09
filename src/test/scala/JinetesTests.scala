import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers._
import types.types._
import participante._
import participante.dragon._
import participante.dragon.dragones._
import participante.items._
import participante.vikingos._
import scala.util.{ Try, Success, Failure}

class JinetesTests extends AnyFreeSpec{

  "Jinetes: " - {
    "Obtener el jinete resultante de un vikingo y un dragón" - {
      "el cual puede tener o no un resultado exitoso, dependiendo si puede montarlo." in {
        val jineteExitoso: Try[Jinete] = Hipo.montar(Chimuelo)
        val jineteNoExitoso: Try[Jinete] = Patan.montar(Chimuelo)
        jineteExitoso.get should be(Jinete(Hipo, Chimuelo))
        a[RuntimeException] should be thrownBy(jineteNoExitoso.get)
      }
    }


    "Un jinete puede cargar tantos kilos de pescado como diferencia haya entre el peso del vikingo y lo que puede cargar el dragón" in {
      //textual enunciado
      val unVikingo: Vikingo = new Vikingo(3, 10, 1, Arma(5))
      val unDragon: Dragon = NadderMortifero(15)
      val unJinete: Jinete = Jinete(unVikingo, unDragon)
      unJinete.capacidadDeCarga should be(unDragon.capacidadDeCarga - unVikingo.peso)
    }

    "El daño que puede realizar un jinete es la suma del daño del vikingo y de su dragón" in {
      //textual enunciado
      val unVikingo: Vikingo = new Vikingo(3, 10, 1, Arma(5))
      val unDragon: Dragon = NadderMortifero(15)
      val unJinete: Jinete = Jinete(unVikingo, unDragon)
      unJinete.danio should be(unVikingo.danio + unDragon.danio)
    }
    "La velocidad de un jinete es la velocidad del dragón menos 1 km/h por cada kilo que pese el vikingo" in {
      //textual enunciado
      val unVikingo: Vikingo = new Vikingo(3, 10, 1, Arma(5))
      val unDragon: Dragon = NadderMortifero(15)
      val unJinete: Jinete = Jinete(unVikingo, unDragon)
      unJinete.velocidad should be(unDragon.velocidad - unVikingo.peso)
    }

  }
/*
  "El jinete resultante es exitoso" - {

    val vikingo: Vikingo = Hipo
    val unDragon: FuriaNocturna = new FuriaNocturna(500, 2)
    val otroDragon: Dragon = ???

    val jineteExitoso: Boolean = vikingo.esJineteExitoso(unDragon)
    (jineteExitoso) should be (true)

  }
*/



}