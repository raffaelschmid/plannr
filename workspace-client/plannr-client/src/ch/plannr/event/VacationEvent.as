package ch.plannr.event
{
	import ch.plannr.model.Vacation;
	
	import flash.events.Event;
	
	public class VacationEvent extends Event
	{
		private var _vacation:Vacation;
		
		public function VacationEvent(id:String, vacation:Vacation=null)
		{
			super(id,true);
			this._vacation=vacation;
		}
		
		public function get vacation():Vacation
		{
			return _vacation;
		}
		
	}
}