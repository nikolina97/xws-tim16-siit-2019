import { Component, OnInit } from '@angular/core';
import { ScientificPaperService } from '../services/scientific-paper.service';
import { PaperLetterDTO } from '../models/paper-letter-dto';
import { Router } from '@angular/router';

declare const Xonomy: any;

@Component({
  selector: 'app-publishing',
  templateUrl: './publishing.component.html',
  styleUrls: ['./publishing.component.css']
})
export class PublishingComponent implements OnInit {

  paper: string = '';
  letter: string = '';

  paperSpec = {};
  letterSpec = {};

  constructor(private paperService: ScientificPaperService, private router: Router) { }

  ngOnInit() {
  }

  public onFileLoad(input: any) {
    let reader = new FileReader();
    reader.onload = () => {
      if (input.target.name == 'paper') {
        this.paper = reader.result as string;
        var editor = document.getElementById("paper_editor");
        Xonomy.render(this.paper, editor, this.paperSpec);
      } else if (input.target.name == 'letter') {
        this.letter = reader.result as string;
        var editor = document.getElementById("letter_editor");
        Xonomy.render(this.letter, editor, this.letterSpec);
      }
    };

    if (input.target.files && input.target.files.length > 0) {
      reader.readAsText(input.target.files[0]);
    }/* else {
      this.paper = '';
    } */
  }

  public submit() {
    this.paper = Xonomy.harvest("paper_editor");
    this.letter = Xonomy.harvest("letter_editor");

    let dto: PaperLetterDTO = {
      paper: this.paper,
      letter: this.letter
    };

    this.paperService.publish(dto).subscribe(
      result => {
        this.router.navigate(['/']);
      },
      error => { }
    );
  }

}
