import {Injectable, NgZone} from '@angular/core';
import {Observable} from 'rxjs';
import {EventSourcePolyfill} from 'ng-event-source';
import {CookieService} from 'ngx-cookie-service';

export interface Item {
  id: string;
  name: string;
  count: string;
  source: string;
}

@Injectable({
  providedIn: 'root',
})
export class SseService {
  private apiUri = 'http://localhost:8081';

  constructor(private zone: NgZone, private cookieService: CookieService) {
  }

  observeMessages(url: string): Observable<Item> {

    const csrf = this.cookieService.get('XSRF-TOKEN');

    const headers = {};
    headers['X-XSRF-TOKEN'] = csrf;

    return new Observable<any>(obs => {
      const eventSource = new EventSourcePolyfill(`${this.apiUri}${url}`, {headers: headers, withCredentials: true});

      eventSource.onmessage = (evt => {
        this.zone.run(() => {
          console.log(evt);
          const data = evt.data != null ? JSON.parse(evt.data) : evt.data;
          obs.next(data);
          console.log('data', data);
        });
      });
      eventSource.onopen = (a) => {
        console.log('open sse', a);
      };
      eventSource.onerror = (e) => {
        console.log('close sse', e);
      };

    });

  }

}
