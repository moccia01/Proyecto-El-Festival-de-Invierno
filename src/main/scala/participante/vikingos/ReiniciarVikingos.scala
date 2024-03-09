package participante.vikingos

import types.types._


object ReiniciarVikingos {
  def apply(vikingos: Vikingos = List(Astrid, Hipo, Patan, Patapez)): Unit = vikingos.foreach(_.hambre = 0)
}
