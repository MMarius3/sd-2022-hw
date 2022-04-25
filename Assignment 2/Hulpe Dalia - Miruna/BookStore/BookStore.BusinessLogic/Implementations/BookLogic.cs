using AutoMapper;
using BookStore.BusinessLogic.Interfaces;
using BookStore.BusinessLogic.Models;
using BookStore.Repository.Interfaces;
using BookStore.Repository.Models;
using CsvHelper;
using Syncfusion.Drawing;
using Syncfusion.Pdf;
using Syncfusion.Pdf.Grid;
using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using IronPdf;
using Magnum.FileSystem;
using System.Text;
using PdfSharp.Drawing;

namespace BookStore.BusinessLogic.Implementations
{
    public class BookLogic : IBookLogic
    {
        public readonly IBookRepository _bookRepository;
        public readonly IMapper _mapper;

        public BookLogic(IBookRepository bookRepository, IMapper mapper)
        {
            _bookRepository = bookRepository;
            _mapper = mapper;
        }

        public async Task<List<BookModel>> GetAllBooksAync()
        {
            var bookEntitys = await _bookRepository.GetAll();

            var books = bookEntitys.Select(b => _mapper.Map<BookModel>(b)).ToList();

            return books;
        }

        public async Task AddBook(BookModel book)
        {
            var bookEntity = _mapper.Map<BookEntity>(book);

            await _bookRepository.Add(bookEntity);
        }

        public async Task DeleteBook(Guid id)
        {
            await _bookRepository.Delete(id);
        }

        public async Task UpdateBook(BookModel book)
        {
            var bookEntity = _mapper.Map<BookEntity>(book);

            var result = await _bookRepository.Update(bookEntity);
        }

        public async Task<BookModel> GetByTitleAsync(string title)
        {
            var bookList = await _bookRepository.FindByTitleAsync(title);
            var bookEntity = bookList.FirstOrDefault();

            var book = _mapper.Map<BookModel>(bookEntity);

            return book;
        }

        public async Task<List<BookModel>> FindByTitleAsync(string title)
        {
            var bookEntitys = await _bookRepository.FindByTitleAsync(title);

            var books = bookEntitys.Select(b => _mapper.Map<BookModel>(b)).ToList();

            return books;
        }

        public async Task<List<BookModel>> FindByGenreAsync(string genre)
        {
            var bookEntitys = await _bookRepository.FindByGenreAsync(genre);

            var books = bookEntitys.Select(b => _mapper.Map<BookModel>(b)).ToList();

            return books;
        }

        public async Task<List<BookModel>> FindByAuthorAsync(string author)
        {
            var bookEntitys = await _bookRepository.FindByAuthorAsync(author);

            var books = bookEntitys.Select(b => _mapper.Map<BookModel>(b)).ToList();

            return books;
        }

        public async Task<BookModel> FindByIdAsync(Guid id)
        {
            var bookEntity = await _bookRepository.FindByIdAsync(id);

            var book = _mapper.Map<BookModel>(bookEntity);

            return book;
        }

        public async Task CreateCSVFile()
        {
            var books = await _bookRepository.GetSoldOutBooksAsync();

            var path = $"{System.IO.Directory.GetCurrentDirectory()}{@"\wwwroot\CSVFiles"}";

            using (var write = new StreamWriter(path + "\\OutOfStock.csv"))
            using (var csv = new CsvWriter(write, CultureInfo.InvariantCulture))
            {
                csv.WriteRecords(books);
            }
        }

        //Library: Syncfusion
        public async Task CreatePdfFile()
        {
            var books = await _bookRepository.GetSoldOutBooksAsync();

            var doc = new Syncfusion.Pdf.PdfDocument();
            var page = doc.Pages.Add();
            var pdfGrid = new PdfGrid();
            var dataTable = new DataTable();
            dataTable.Columns.Add("Id");
            dataTable.Columns.Add("Title");
            dataTable.Columns.Add("Author");
            dataTable.Columns.Add("Genre");
            dataTable.Columns.Add("Quantity");
            dataTable.Columns.Add("Price");

            foreach (var book in books)
            {
                dataTable.Rows.Add(new object[] { book.Id, book.Title, book.Author, book.Genre, book.Quantity, book.Price });
            }
            pdfGrid.DataSource = dataTable;
            pdfGrid.Draw(page, new PointF(10, 10));

            var stream = new MemoryStream();
            doc.Save(stream);
            stream.Close();
            doc.Close(true);
        }

        //Library: IronPdf
        public async Task CreatePdfFileMethod2()
        {
            var books = await _bookRepository.GetSoldOutBooksAsync();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.Append("<table>");
            stringBuilder.Append("<thead>");
            stringBuilder.Append("<tr class=\"table-head\">");
            stringBuilder.Append("<th>Id</th> <th>Title</th> <th>Author</th> <th>Genre</th> <th>Price</th> <th>Quantity</th> </tr> </thead> <tbody>");

            foreach (var book in books)
            {
                stringBuilder.Append("<tr>");
                stringBuilder.Append($"<th>{book.Id}</th> <th>{book.Title}</th> <th>{book.Author}</th> <th>{book.Genre}</th> <th>{book.Price}</th> <th>{book.Quantity}</th>");
                stringBuilder.Append("</tr>");
            }

            stringBuilder.Append(" </tbody> </table>");

            var htmlText = stringBuilder.ToString();
            var HtmlLine = new HtmlToPdf();

            HtmlLine.RenderHtmlAsPdf(htmlText).SaveAs("SoldOutBooks2.pdf");
        }

        //Library: PdfSharp
        public async Task CreatePdfFileMethod3()
        {
            var books = await _bookRepository.GetSoldOutBooksAsync();

            var document = new PdfSharp.Pdf.PdfDocument();

            document.Info.Title = "Sold out books";

            var page = document.AddPage();

            var gfx = XGraphics.FromPdfPage(page);

            var font = new XFont("TimesNewRoman", 12, XFontStyle.Regular);

            gfx.DrawString("Id Title Author Genre Quantity Price", font, XBrushes.Black, new XPoint (20, 70));

            int i = 1;
            foreach (var book in books)
            {
                gfx.DrawString($"{book.Id} {book.Title} {book.Author} {book.Genre} {book.Quantity} {book.Price}", font, XBrushes.Black, new XPoint(20, 70+i*15));
                i++;
            }

            var filename = "SoldOutBooks3.pdf";
            document.Save(filename);
        }
    }
}
