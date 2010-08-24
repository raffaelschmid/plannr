package ch.plannr.event
{
	import ch.plannr.model.User;
	
	import flash.events.Event;
	
	public class UserRegisterEvent extends Event
	{
		public var user:User;
		
		public function UserRegisterEvent()
		{
			super(Events.REGISTRATION_ATTEMPT,true);
		}
		
	}
}