import { Component, OnInit, Inject } from '@angular/core';
import { ReviewService } from '../services/review.service';
import { ActivatedRoute } from '@angular/router';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-assign-reviewer',
  templateUrl: './assign-reviewer.component.html',
  styleUrls: ['./assign-reviewer.component.css']
})
export class AssignReviewerComponent implements OnInit {

  displayedColumns: string[] = ['firstName', 'lastName', 'email', 'expertise', 'add'];
  dataSource : any[] = [];
  paperId = "";

  constructor(private reviewService : ReviewService, private route: ActivatedRoute, private dialog: MatDialog ) { }

  ngOnInit() {
    this.getRecommendedReviewers();
  }

  getRecommendedReviewers() {
    this.paperId = this.route.snapshot.paramMap.get('paperId')
    this.reviewService.getRecommendedReviewers(this.paperId).subscribe(
      (result) => {
        this.dataSource = result;
      }
    )
  }

  addReviewer(email: string) {
    this.paperId = this.route.snapshot.paramMap.get('paperId')
    this.reviewService.addReviewer(this.paperId, email).subscribe(
      (result) => {
        if (result == true) {
          alert("Successfully added reviewer.")
          window.location.reload();
        }
      }
    )
  }

  showExpertise(expertise : any[]) {
    this.dialog.open(ExpertiseDialog, {
      data :{
        expertise
      } 
    });
  }
}

@Component({
  selector: 'expertise-popup',
  templateUrl: 'expertise-popup.html',
})
export class ExpertiseDialog {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}
}
