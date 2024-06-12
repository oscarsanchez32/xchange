import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  template: `
    <section class="">
      <footer class="text-center text-white bg-dark border-custom-orange-top footer-bottom">
        <div class="text-center p-2">
          <div class="text-muted">© 2024 XCHANGE</div>
        </div>
      </footer>
    </section>
  `,
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

}
