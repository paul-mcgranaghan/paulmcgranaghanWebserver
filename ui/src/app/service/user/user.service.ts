import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../../model/user';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class UserService {

  private usersUrl: string;
  private addUserUrl: string;

  constructor(private http: HttpClient) {

    this.usersUrl = 'http://localhost:8090/users/listAll';
    this.addUserUrl = 'http://localhost:8090/users/addUser';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public save(user: User): Observable<User> {
    return this.http.post<User>(this.addUserUrl, user);
  }
}
