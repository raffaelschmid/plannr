package ch.plannr.event
{
	import ch.plannr.model.Team;
	import ch.plannr.model.User;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	
	public class AddMembersToTeamEvent extends Event
	{
		private var _members:ArrayCollection;
		
		public function AddMembersToTeamEvent(members:ArrayCollection)
		{
			super(Events.MEMBERS_ADD_EVENT,true);
			this._members=members;
		}
		
		public function get members():ArrayCollection
		{
			return _members;
		}
		
	}
}