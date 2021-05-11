import {HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";

@Injectable()
export class RefreshInterceptor implements HttpInterceptor{

  constructor(private http : HttpClient) {
    console.log("进入RefreshInterceptor")
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(

      tap(
        ()=>{},
        error => {
          console.log(error);
          if(error.status === 500 && error.error.message === "refresh fail"){
            //方案一 refresh_token也失效了 直接退出 重新登录
            //this.logout();

            //方案二 refresh_token也失效了 看看认证服务器的session失效没有 没有失效 就能重新获取一个token
            window.location.href="http://127.0.0.1:7777/oauth/authorize?"
              +"client_id=front-server"
              +"&redirect_uri=http://127.0.0.1:8080/oauth/callback"
              +"&response_type=code"
              +"&state=index.html";
          }
        }
      )
    );
  }

  //方案一
  // logout() {
  //   this.http.get("logout").subscribe(
  //     ()=>{
  //       alert("登录超时，请重新登录");
  //       window.location.href="http://127.0.0.1:7777/logout?redirect_uri=http://127.0.0.1:8080";
  //     }
  //   )
  // }

}
