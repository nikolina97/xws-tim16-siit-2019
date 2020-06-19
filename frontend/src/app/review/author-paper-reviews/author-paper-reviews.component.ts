import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ReviewService } from '../services/review.service';
import { AuthService } from 'src/app/user/services/auth.service';

@Component({
  selector: 'app-author-paper-reviews',
  templateUrl: './author-paper-reviews.component.html',
  styleUrls: ['./author-paper-reviews.component.css']
})
export class AuthorPaperReviewsComponent implements OnInit {

  id:string = null;
  displayedColumns: string[] = ["review", "html", "pdf"];
  dataSource : any[] = [];
  constructor(private activatedRoute: ActivatedRoute, private auth:AuthService, private reviewService: ReviewService) { }

  ngOnInit() {
   this.activatedRoute.params.subscribe(params => {
      this.id = params['paperId'];
      console.log(this.id);
   });
   this.getReviews();
  }

  getReviews(){
    this.reviewService.getReviewersForPapper(this.id).subscribe(
      data=>{
        console.log(data);
        this.dataSource = data;
      },
      error=>{
        console.log(error.error);
      }
    )
  }

  getHtml(review:any){
    this.reviewService.getHtml(review).subscribe(
    data =>{
      const blob = new Blob([data], { type: 'text/html' });
      const url= window.URL.createObjectURL(blob);
      window.open(url);
    },
    error => {console.log(error.error);}
    )
  }
}
