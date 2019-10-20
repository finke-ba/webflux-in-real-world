import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';

@Injectable()
export class UrlInterceptor implements HttpInterceptor {
  private apiUri = 'http://localhost:8081';

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const apiReq = req.clone({ url: `${this.apiUri}${req.url}` });
    return next.handle(apiReq);
  }

}
