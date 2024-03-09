package participante.dragon

import participante.Vikingo

abstract class Dragon(){
  val peso: Double
  val velocidadBase: Double = 60.0
  val requisitosMontura: RequisitosMontura

  def danio: Double
  def velocidad: Double = (velocidadBase - peso).max(0)

  def capacidadDeCarga: Double = peso * 0.2
  def puedeSerMontadoPor(vikingo: Vikingo): Boolean =
    vikingo.peso <= this.capacidadDeCarga && requisitosMontura.cumpleRequisitos(vikingo, this)

}
