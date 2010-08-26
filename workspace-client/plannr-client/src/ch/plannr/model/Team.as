package ch.plannr.model
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;

	[Bindable]
	public class Team
	{
	
		private var _id:int = 0;
		private var _name:String=null;
		private var _description:String=null;
		
		private var _members:ArrayCollection = new ArrayCollection();
		
		public function set id(value:int):void
		{
			_id= value;
		} 
		
		public function get id():int
		{
			return _id;
		}
		
		public function set name(value:String):void
		{
			_name= value;
		} 
		
		public function get name():String
		{
			return _name;
		}
		
		public function set description(value:String):void
		{
			_description= value;
		} 
		
		public function get description():String
		{
			return _description;
		}
		
		public function addMember(user:User):void{
			_members.addItem(user);
		}
		
		public function set members(value:ArrayCollection):void
		{
			_members= value;
		} 
		public function get members():ArrayCollection
		{
			return _members;
		}
		
		public static function fromXml(xml:XML):Team{
			var team:Team = new Team();
			team.id=parseInt(xml.id);
			team.name=xml.name;
			team.description=xml.description;
			
			for each(var m:XML in xml.members.user){
				var us:User = User.fromXml(m);
				team.addMember(us);
			}
			
			return team;
		}
		public function toString():String{
			return members.toString();
		}
	}
}