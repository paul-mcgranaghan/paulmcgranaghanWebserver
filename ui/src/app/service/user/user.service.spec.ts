import {TestBed, waitForAsync} from '@angular/core/testing';

import {UserService} from './user.service';
import {mock} from 'ts-mockito';
import {HttpClient} from '@angular/common/http';
import {UserFormComponent} from '../../user-form/user-form.component';

describe('UserServiceService', () => {
  let service: UserService = new UserService(mock(HttpClient));

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [UserFormComponent],
      providers: [{
        provide: UserService,
        useValue: new UserService(mock(HttpClient))
      }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
