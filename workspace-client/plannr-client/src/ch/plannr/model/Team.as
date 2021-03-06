package ch.plannr.model
{
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.xml.SimpleXMLEncoder;

	[Bindable]
	public class Team
	{
		
		public function Team(name:String=null, description:String=null,id:int=0){
			this._id=id;
			this._name=name;
			this._description=description;
		}
	
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
		
		public function toXml():XML {
			var qName:QName = new QName("team");
			var xmlDocument:XMLDocument = new XMLDocument();
			var simpleXMLEncoder:SimpleXMLEncoder = new SimpleXMLEncoder(xmlDocument);
			var xmlNode:XMLNode = simpleXMLEncoder.encodeValue(this, qName, xmlDocument);
			var xml:XML = new XML(xmlDocument.toString());
			return xml;
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