import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrls: ['./cupboard.component.css']
})
export class CupboardComponent implements OnInit{
  cupboard: Need[] = []; 

  constructor(private needService: NeedService) { }

  ngOnInit(): void {
    this.getCupboard();
  }

  getCupboard(): void {
    this.needService.getCupboard()
    .subscribe(cupboard => this.cupboard = cupboard);
  }
}
