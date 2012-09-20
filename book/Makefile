BOOK=icpcbook
BOOKPDF=icpcbook.pdf
BOOKSRC=icpcbook.tex

all: $(BOOKPDF)

$(BOOKPDF): $(wildcard *.tex) references.bib
	pdflatex -shell-escape $(BOOKSRC)
	makeindex $(BOOK)
	bibtex $(BOOK)
	pdflatex -shell-escape $(BOOKSRC)
	pdflatex -shell-escape $(BOOKSRC)

