import {Component} from '@angular/core';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  public currentTitle: string = "Temperature";
  public currentSubtitle: string = "eau";
  public currentValue: number;



}
