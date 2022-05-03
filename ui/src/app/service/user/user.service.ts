import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../../model/user';
import {Observable} from 'rxjs';

@Injectable()
export class UserService {

  private readonly usersUrl: string;
  private readonly addUserUrl: string;
  private login: string;

  constructor(private http: HttpClient) {

    this.usersUrl = 'http://localhost:8090/users/listAll';
    this.addUserUrl = 'http://localhost:8090/users/addUser';
    this.login = 'http://localhost:8090/';
  }

  public findAll(): Observable<User[]> {
    return this.http.get<User[]>(this.usersUrl);
  }

  public save(user: User): Observable<User> {
    return this.http.post<User>(this.addUserUrl, user);
  }
}
