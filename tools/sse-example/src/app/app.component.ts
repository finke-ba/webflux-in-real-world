import {Component} from '@angular/core';
import {Item, SseService} from "./sse.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  title = 'sse-example';
  messages: Item[] = [];

  constructor(private  sseService: SseService, private http: HttpClient) {
  }

  getHealth() {
    this.http.get('/health', {withCredentials: true}).subscribe(value => console.log(value));
  }

  login() {
    this.http.post('/login', {name: 'name', password: 'password'}, {withCredentials: true}).subscribe(value => console.log(value));
  }

  sendRequest() {
    this.sseService.observeMessages('/stream').subscribe(value => this.messages.push(value));
  }

}
