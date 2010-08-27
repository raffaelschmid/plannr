package ch.plannr.event
{
	import ch.plannr.model.User;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	
	public class DeleteMemberFromTeamEvent extends Event
	{
		private var _member:User;
		
		public function DeleteMemberFromTeamEvent(member:User)
		{
			super(Events.MEMBERS_REMOVE_EVENT,true);
			this._member=member;
		}
		
		public function get member():User
		{
			return _member;
		}
		
	}
}