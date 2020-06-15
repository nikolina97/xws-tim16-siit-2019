import { Component, OnInit } from '@angular/core';
import { ScientificPaperDTO } from "../models/scientific-paper-dto";
import { ScientificPaperService } from '../services/scientific-paper.service';
import { CoverLetterService } from 'src/app/cover-letter/services/cover-letter.service';
import { Router } from '@angular/router';
import { PaperLetterDTO } from '../models/paper-letter-dto';

declare const Xonomy: any;

@Component({
  selector: 'app-publication',
  templateUrl: './publication.component.html',
  styleUrls: ['./publication.component.css']
})
export class PublicationComponent implements OnInit {

  paper: string = '';
  letter: string = '';

  paperSpec = {};
  letterSpec = {};

  constructor(private paperService: ScientificPaperService, private letterService: CoverLetterService, private router: Router) { }

  ngOnInit(): void {

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
