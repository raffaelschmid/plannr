package ch.plannr.model
{
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	
	import mx.controls.Alert;
	import mx.rpc.xml.SimpleXMLEncoder;

	[Bindable]
	public class User
	{
		private var _id:int;
		private var _firstname:String = null;
		private var _lastname:String=null;
		private var _email:String=null;
		private var _password:String = null;
		public var address:Address = new Address();
		
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
			return xml;
		}
		
		public static function fromXml(xml:XML):User{
			var user:User = new User();
			
			user.id=parseInt(xml.id);
			user.firstname=xml.firstname;
			user.lastname=xml.lastname;
			user.email=xml.email;
			user.address.street1=xml.address.street1[0];
			user.address.street2=xml.address.street2[0];
			user.address.city=xml.address.city[0];
			user.address.countryCode=xml.address.countryCode[0];
			user.address.zip=parseInt(xml.address.zip[0]);
			
			
			return user;
		}

		public function toString():String{
			return firstname + " " + lastname + " " + address.street1 + " " + address.city;
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
		public function get address_zip():int
		{
			return address.zip;
		}
		public function get address_city():String
		{
			return address.city;
		}
		public function get address_street1():String
		{
			return address.street1;
		}
		public function get address_street2():String
		{
			return address.street2;
		}
	}
}