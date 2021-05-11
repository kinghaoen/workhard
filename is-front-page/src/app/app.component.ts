import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = '前端服务器';
  authenticated = false;
  // credentials = {username: '192168092161547461069282',password: '123456'};
  order = {
    id: String,
    price: String
  };

  constructor(private http : HttpClient) {
    this.http.get("me").subscribe(
      data=>{
        if(data){
          this.authenticated = true;
          console.log('前端服务器获取到的token：'+data)
        }
        //如果没有登录
        if(!this.authenticated){
          //直接跳转到认证中心提供的登录页面
          window.location.href="http://127.0.0.1:7777/oauth/authorize?"
            +"client_id=front-server"
            +"&redirect_uri=http://127.0.0.1:8080/oauth/callback"
            +"&response_type=code"
            +"&state=index.html";
        }
      }
    )
  }

/*
  login(){
      this.http.post("login",this.credentials).subscribe(
        ()=>{
          this.authenticated = true;
        },() => {
          console.log("登录失败")
        }
      )
  }
*/

  getOrder() {
    this.http.get("api/order/order/1").subscribe(
      data=>{
        console.log(data)
        // @ts-ignore
        this.order = data;
      },(error) => {
        console.log("登录失败"+error)
      }
    )
  }

  logout() {
    this.http.get("logout").subscribe(
      ()=>{
        //todo？ 是否是7777里的logout 还是6666认证中心里的logout
        //fix 确认7777是认证中心端口号 8888是网关端口号
        window.location.href="http://127.0.0.1:7777/logout?redirect_uri=http://127.0.0.1:8080";
        // this.authenticated = false;
      },() => {
        console.log("退出失败")
      }
    )
  }
}
