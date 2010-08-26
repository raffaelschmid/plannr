package ch.plannr.service
{
	import mx.controls.Alert;
	import mx.rpc.http.HTTPService;
	import mx.utils.Base64Encoder;

	public class HttpServiceFactory
	{
		[Inject(source="config.rootUrl")]
		public var rootUrl:String;
		
		[Bindable]
		[Inject(source="context.email", bind="true")]
		public var email:String = null;
		
		[Bindable]
		[Inject(source="context.password", bind="true")]
		public var password:String = null;
		
		
		public function postService(url:String,authentication:Boolean=false):HTTPService{
			var service:HTTPService = new HTTPService();
			service.method="POST";
			service.contentType="application/xml";
			service.resultFormat=HTTPService.RESULT_FORMAT_E4X;
			service.showBusyCursor=true;
			service.url = rootUrl+url;
			service.headers = getAuthorizationHeaders(authentication,"POST");
			
			return service;
		}
		
		public function getService(url:String,authentication:Boolean=false):HTTPService{
			var service:HTTPService = new HTTPService();
			service.method="GET";
			service.resultFormat=HTTPService.RESULT_FORMAT_E4X;
			service.showBusyCursor=true;
			service.url = rootUrl+url;
			service.headers = getAuthorizationHeaders(authentication,"GET");
			
			return service;
		}
		
		public function deleteService(url:String,authentication:Boolean=false):HTTPService{
			var service:HTTPService = new HTTPService();
			service.contentType="application/xml"
			service.method="DELETE";
			service.resultFormat=HTTPService.RESULT_FORMAT_E4X;
			service.showBusyCursor=true;
			service.url = rootUrl+url;
			service.headers = getAuthorizationHeaders(authentication,"DELETE");
			
			return service;
		}
		
		private function getAuthorizationHeaders(authentication:Boolean, method:String):Array
		{
			var headers:Array = new Array();
			
			if(authentication){
				var encoder:Base64Encoder=new Base64Encoder();
				encoder.encode(this.email + ":" + this.password);
				headers["Authorization"]="Basic " + encoder.toString();
			}
			if(method=="DELETE" || method=="PUT"){
				headers["X-HTTP-Method-Override"]=method;
			}
				
			return headers;
		}
	}
}