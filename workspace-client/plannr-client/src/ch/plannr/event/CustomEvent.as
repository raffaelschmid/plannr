package ch.plannr.event
{
	import ch.plannr.model.User;
	
	import flash.events.Event;
	
	public class CustomEvent extends Event
	{
		public function CustomEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
	}
}