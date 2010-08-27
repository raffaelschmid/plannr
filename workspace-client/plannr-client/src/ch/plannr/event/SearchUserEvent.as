package ch.plannr.event
{
	import ch.plannr.model.Team;
	import ch.plannr.model.User;
	
	import flash.events.Event;
	
	public class SearchUserEvent extends Event
	{
		private var _term:String;
		
		public function SearchUserEvent(term:String)
		{
			super(Events.SEARCH_USERS,true);
			this._term=term;
		}
		
		public function get term():String
		{
			return _term;
		}
		
	}
}