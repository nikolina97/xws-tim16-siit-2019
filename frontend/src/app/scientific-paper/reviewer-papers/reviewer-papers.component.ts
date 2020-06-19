import { Component, OnInit } from '@angular/core';
import { ScientificPaperService } from '../services/scientific-paper.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reviewer-papers',
  templateUrl: './reviewer-papers.component.html',
  styleUrls: ['./reviewer-papers.component.css']
})
export class ReviewerPapersComponent implements OnInit {

  displayedColumns: string[] = ['id', 'title', 'category', 'version', 'dateReceived', "state", "html", "accept", "reject"];
  dataSource: any[] = [];
  constructor(private paperService: ScientificPaperService, private router: Router) { }

  ngOnInit() {
    this.getPapers();
  }

  getPapers() {
    this.paperService.getReviewersPapers().subscribe(
      (result) => {
        this.dataSource = result;
        console.log(this.dataSource);
      }
    )
  }

  accept(spId: string) {
    this.paperService.accept(spId).subscribe(
      result => {
        this.router.navigate(['/review/write-review', spId]);
      },
      error => { console.log(error.error()) }
    )
  };

  reject(spId: string) {
    this.paperService.reject(spId).subscribe(
      result => { this.getPapers() },
      error => { console.log(error.error()) }
    )
  };
}
