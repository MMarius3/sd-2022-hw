using AutoMapper;
using BookStore.BusinessLogic.Interfaces;
using BookStore.BusinessLogic.Models;
using BookStore.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace BookStore.Controllers
{
    public class BooksController : Controller
    {
        public readonly IBookLogic _bookLogic;
        public readonly IMapper _mapper;

        public BooksController(IBookLogic bookLogic, IMapper mapper)
        {
            _bookLogic = bookLogic;
            _mapper = mapper;
        }

        [HttpGet]
        public IActionResult AddBook()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> AddBook(BookViewModel bookViewModel)
        {
            if (!ModelState.IsValid)
            {
                return View(bookViewModel);
            }

            var bookModel = _mapper.Map<BookModel>(bookViewModel);
            await _bookLogic.AddBook(bookModel);

            return RedirectToAction("Admin", "Accounts");
        }

        [HttpGet]
        public async Task<IActionResult> DeleteBook(Guid id)
        {
            await _bookLogic.DeleteBook(id);

            return RedirectToAction("Admin", "Accounts");
        }

        [HttpGet]
        public async Task<IActionResult> UpdateBook(Guid id)
        {
            var bookEdit = new EditBookViewModel();

            var bookModel = await _bookLogic.FindByIdAsync(id);

            var book = _mapper.Map<BookViewModel>(bookModel);

            bookEdit.OldBook = bookModel.Title;
            bookEdit.NewBook = book;

            return View(bookEdit);
        }

        [HttpPost]
        public async Task<IActionResult> UpdateBook(EditBookViewModel editBookViewModel)
        {
            if (!ModelState.IsValid)
            {
                return View(editBookViewModel);
            }

            var bookModel = _mapper.Map<BookModel>(editBookViewModel.NewBook);
            await _bookLogic.UpdateBook(bookModel);

            return RedirectToAction("Admin", "Accounts");
        }

        [HttpGet]
        public async Task<IActionResult> SellBook(Guid id)
        {
            var bookModel = await _bookLogic.FindByIdAsync(id);
            if (bookModel.Quantity != 0)
            {
                bookModel.Quantity--;
            }

            await _bookLogic.UpdateBook( bookModel);

            return RedirectToAction("Employee", "Accounts");
        }

        [HttpGet]
        public async Task<IActionResult> GetSoldOutBooks()
        {
            await _bookLogic.CreateCSVFile();
            await _bookLogic.CreatePdfFile();
            await _bookLogic.CreatePdfFileMethod2();
            await _bookLogic.CreatePdfFileMethod3();

            return RedirectToAction("Admin", "Accounts");
        }
    }
}
