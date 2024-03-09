package torneo.postas
import types.types._
import participante._
import participante.dragon._
import scala.util.{ Try, Success, Failure}


abstract class Posta(
                      val requisitos: Condiciones,
                      val mejorQue: Orden
                    ) {

  private def puedeParticipar(participante: Participante): Boolean = {
    requisitos.forall(_(participante) && participante.puedeParticiparEn(this))
  }
  def puedenParticipar(participantes: Participantes): Participantes = participantes.filter(puedeParticipar)

  def ganadores(participantes: Participantes): Vikingos = {
    //Los participantes que recibe ya se evaluaron si pueden o no participar y están "preparados" segun regla y posta
    participantes.map(_.participarEn(this)).sortWith(mejorQue).map(_.vikingod())
    //Usa un mensaje polimorfico (sigue usando match pero solo en la implementacion del metodo dentro de participante)

    /*participantes.map(_.participarEn(this)).sortWith(mejorQue).map({
      case Jinete(vikingo, _) => vikingo
      case vikingo: Vikingo => vikingo
    })*/

  }

  def mejorFormaDeParticiparOption(vikingo: Vikingo, dragones: Dragones): Option[Participante] = {
    //obtener jinetes
    val jinetes: List[Jinete] = dragones.flatMap(dragon => vikingo.montar(dragon).toOption)

    //flatMap se queda solo con los valores q haya en un Some, si es None lo ignora y tuve que poner el toOption pq montar devuelve un Try q tiene un flatMap distinto

    //filtro los que puedan participar y Obtengo el que puede montar (Some) o None en caso que ninguno pueda
    puedenParticipar(vikingo::jinetes).sortWith(mejorQue).headOption
  }

}
//(*) NOTA:
//diff retorna la diferencia entre una lista y la otra, donde solo desaparece una vez cada elemento
//es decir que si hubiera más de un dragon igual a drag, solo quita el primero (probado con List[Int] en consola)