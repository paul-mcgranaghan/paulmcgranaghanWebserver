import {Component, HostBinding, OnInit} from '@angular/core';
import {UntypedFormControl} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})

export class AppComponent implements OnInit {

  title: string;

  @HostBinding('class') className = '';
  toggleControl = new UntypedFormControl(false);

  constructor() {
    this.title = 'User Management System';
  }

  ngOnInit(): void {
    this.toggleControl.valueChanges.subscribe((darkMode) => {
      const darkClassName = 'darkMode';
      this.className = darkMode ? darkClassName : '';
    });
  }
}
