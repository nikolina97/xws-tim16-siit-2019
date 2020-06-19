import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../services/review.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ReviewDTO } from '../models/review-dto';

declare const Xonomy: any;

@Component({
  selector: 'app-write-review',
  templateUrl: './write-review.component.html',
  styleUrls: ['./write-review.component.css']
})
export class WriteReviewComponent implements OnInit {

  review: string = '';
  paperId: string;
  reviewSpec = {};

  constructor(private reviewService: ReviewService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
    this.route.paramMap.subscribe(
      pmap => this.paperId = pmap.get("paperId")
    );
  }

  public onFileLoad(input: any) {
    let reader = new FileReader();
    reader.onload = () => {
      this.review = reader.result as string;
      var editor = document.getElementById("editor");
      Xonomy.render(this.review, editor, this.reviewSpec);
    };

    if (input.target.files && input.target.files.length > 0) {
      reader.readAsText(input.target.files[0]);
    }/* else {
      this.paper = '';
    } */
  }

  public submit() {
    this.review = Xonomy.harvest("editor");

    let dto: ReviewDTO = {
      review: this.review,
      paperId: this.paperId
    };

    this.reviewService.post(dto).subscribe(
      (result) => {
        this.router.navigate(['']);
      },
      (error) => {
        // this.router.navigate(['']);
      }
    );
  }

}
