import { Component, OnInit } from '@angular/core';
import { ScientificPaperService } from '../services/scientific-paper.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-papers-from-author',
  templateUrl: './papers-from-author.component.html',
  styleUrls: ['./papers-from-author.component.css']
})
export class PapersFromAuthorComponent implements OnInit {

  displayedColumns: string[] = ['id', 'title', 'category', 'version', 'dateReceived', "state", "button"];
  dataSource : any[] = [];

  constructor(private paperService: ScientificPaperService, private router: Router) { }

  ngOnInit() {
    this.getPapers()
  }

  getPapers() {
    this.paperService.getMyPapers().subscribe(
      (result) => {
        this.dataSource = result;
        console.log(this.dataSource);
      }
    )
  }
  revoke(element) { 
    this.paperService.revokePaper(element.id).subscribe(
      (result) => {
        this.getPapers();
      }
    )
    console.log(element)
  }
}
