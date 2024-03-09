package participante.dragon
import participante._
case class RequisitosMontura(requisitos: Set[(Vikingo, Dragon) => Boolean]) {

  def cumpleRequisitos(vikingo: Vikingo, dragon: Dragon):Boolean = {
    requisitos.forall(req => req(vikingo, dragon))
  }
}

//object RequisitoVacio extends RequisitosMontura(Set[(Vikingo, Dragon) => Boolean]())
