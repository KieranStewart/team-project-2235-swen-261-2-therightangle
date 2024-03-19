import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

@Component({
  selector: 'app-confirmation-view',
  templateUrl: './confirmation-view.component.html',
  styleUrls: ['./confirmation-view.component.css']
})
export class ConfirmationViewComponent implements OnInit{
  state: string | null = "";
  constructor(private route: ActivatedRoute, private router: Router) { }
  
  ngOnInit(): void {
    this.state = this.route.snapshot.paramMap.get('state');
  }
}
