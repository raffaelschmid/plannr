package ch.plannr.model
{
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	
	import mx.rpc.xml.SimpleXMLEncoder;

	public class User
	{
		private var _id:int;
		private var _firstname:String = null;
		private var _lastname:String=null;
		private var _email:String=null;
		private var _password:String = null;
		
		public function User(firstname:String=null, lastname:String=null, email:String=null, password:String=null)
		{
			this._firstname=firstname;
			this._lastname=lastname;
			this._email=email;
			this._password=password;
		}
		
		public function toXml():XML {
			var qName:QName = new QName("user");
			var xmlDocument:XMLDocument = new XMLDocument();
			var simpleXMLEncoder:SimpleXMLEncoder = new SimpleXMLEncoder(xmlDocument);
			var xmlNode:XMLNode = simpleXMLEncoder.encodeValue(this, qName, xmlDocument);
			var xml:XML = new XML(xmlDocument.toString());
			trace(xml.toXMLString());
			return xml;
		}

		
		
		public function set firstname(firstname:String):void
		{
			_firstname= firstname;
		}
		
		public function get firstname():String
		{
			return _firstname;
		}
		
		public function set lastname(lastname:String):void
		{
			_lastname= lastname;
		}
		
		public function get lastname():String
		{
			return _lastname;
		}
		
		public function set email(email:String):void
		{
			_email= email;
		}
		
		public function get email():String
		{
			return _email;
		}
		
		public function set password(password:String):void
		{
			_password= password;
		}
				
		public function get password():String
		{
			return _password;
		}
		
		public function set id(value:int):void
		{
			_id= value;
		} 
		public function get id():int
		{
			return _id;
		}
	}
}