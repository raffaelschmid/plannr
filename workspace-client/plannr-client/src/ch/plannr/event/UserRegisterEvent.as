package ch.plannr.event
{
	import ch.plannr.model.User;
	
	import flash.events.Event;
	
	public class UserRegisterEvent extends Event
	{
		private var _user:User;
		
		public function UserRegisterEvent(user:User)
		{
			super(Events.REGISTRATION_ATTEMPT,true);
			_user=user;
		}
		public function get user():User
		{
			return _user;
		}
		
	}
}