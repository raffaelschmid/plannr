package ch.plannr.event
{
	import ch.plannr.model.Team;
	import ch.plannr.model.User;
	
	import flash.events.Event;
	
	public class TeamEvent extends Event
	{
		private var _team:Team;
		
		public function TeamEvent(id:String, team:Team)
		{
			super(id,true);
			this._team=team;
		}
		
		public function get team():Team
		{
			return _team;
		}
		
	}
}