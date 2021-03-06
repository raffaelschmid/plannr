package ch.plannr.service
{
	import mx.controls.Alert;
	import mx.core.FlexGlobals;
	import mx.events.BrowserChangeEvent;
	import mx.managers.BrowserManager;
	import mx.managers.IBrowserManager;
	import mx.rpc.http.HTTPService;
	import mx.utils.Base64Encoder;
	import mx.utils.URLUtil;

	public class HttpServiceFactory
	{
		public var rootUrl:String=showURLDetails();
		
		[Bindable]
		[Inject(source="context.email", bind="true")]
		public var email:String = null;
		
		[Bindable]
		[Inject(source="context.password", bind="true")]
		public var password:String = null;
		
		private function showURLDetails():String {
			
			var baseURL:String = null;
			var temp:IBrowserManager = BrowserManager.getInstance();
			temp.init();
			baseURL = temp.url;
				
			if(baseURL.substr(0,5)=="file:")
				baseURL="http://localhost:8080";
			else
				baseURL="";
			
			return baseURL;
		}
		
		
		public function postService(url:String,authentication:Boolean=false):HTTPService{
			var service:HTTPService = new HTTPService();
			service.method="POST";
			service.contentType="application/xml";
			service.resultFormat=HTTPService.RESULT_FORMAT_E4X;
			service.showBusyCursor=true;
			service.url = rootUrl+url;
			service.headers = restHeader(authentication,"POST");
			
			return service;
		}
		
		public function putService(url:String,authentication:Boolean=false):HTTPService{
			var service:HTTPService = new HTTPService();
			service.method="PUT";
			service.contentType="application/xml";
			service.resultFormat=HTTPService.RESULT_FORMAT_E4X;
			service.showBusyCursor=true;
			service.url = rootUrl+url;
			service.headers = restHeader(authentication,"PUT");
			
			return service;
		}
		
		public function getService(url:String,authentication:Boolean=false):HTTPService{
			var service:HTTPService = new HTTPService();
			service.method="GET";
			service.resultFormat=HTTPService.RESULT_FORMAT_E4X;
			service.showBusyCursor=true;
			service.url = rootUrl+url;
			service.headers = restHeader(authentication,"GET");
			
			return service;
		}
		
		public function deleteService(url:String,authentication:Boolean=false):HTTPService{
			var service:HTTPService = new HTTPService();
			service.contentType="application/xml"
			service.method="DELETE";
			service.resultFormat=HTTPService.RESULT_FORMAT_E4X;
			service.showBusyCursor=true;
			service.url = rootUrl+url;
			service.headers = restHeader(authentication,"DELETE");
			
			return service;
		}
		
		private function restHeader(authentication:Boolean, method:String):Array
		{
			var headers:Array = new Array();
			if(method=="DELETE" || method=="PUT"){
				headers["X-HTTP-Method-Override"]=method;
			}
				
			return headers;
		}
	}
}