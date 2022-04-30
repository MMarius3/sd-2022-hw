using AutoMapper;
using BookStore.BusinessLogic.Interfaces;
using BookStore.BusinessLogic.Models;
using BookStore.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

namespace BookStore.Controllers
{
    public class AccountsController : Controller
    {
        public readonly IBookLogic _bookLogic;
        public readonly IMapper _mapper;
        public readonly UserManager<IdentityUser> _userManager;

        public AccountsController(IBookLogic bookLogic, IMapper mapper, UserManager<IdentityUser> userManager)
        {
            _bookLogic = bookLogic;
            _mapper = mapper;
            _userManager = userManager;
        }

        [HttpGet]
        public async Task<IActionResult> Admin()
        {
            var users = _userManager.Users.ToList();
            var booksModel = await _bookLogic.GetAllBooksAync();

            var books = booksModel.Select(b => _mapper.Map<BookViewModel>(b)).ToList();

            var adminViewModel = new AdminViewModel();
            adminViewModel.Books = books;
            adminViewModel.Users = users;
            return View(adminViewModel);
        }

        [HttpGet]
        public async Task<IActionResult> DeleteUser(string email)
        {
            var user = await _userManager.FindByEmailAsync(email);
            await _userManager.DeleteAsync(user);

            return RedirectToAction(nameof(Admin));
        }

        [HttpGet]
        public async Task<IActionResult> UpdateUser(string email)
        {
            var user = await _userManager.FindByEmailAsync(email);
            var updateUser = new UserViewModel();
            updateUser.Email = email;
            updateUser.UserName = user.UserName;

            return View(updateUser);
        }

        [HttpPost]
        public async Task<IActionResult> UpdateUser(UserViewModel updateUser)
        {
            var user = await _userManager.FindByEmailAsync(updateUser.Email);
            user.UserName = updateUser.UserName;
            await _userManager.UpdateAsync(user);

            return RedirectToAction(nameof(Admin));
        }

        [HttpGet]
        public IActionResult AddUser()
        {
            return View();
        }

        [HttpPost]
        public async Task<IActionResult> AddUser(UserViewModel userData)
        {
            var user = new IdentityUser { UserName = userData.UserName, Email = userData.Email };
            var result = await _userManager.CreateAsync(user, userData.Password);
            await _userManager.AddToRoleAsync(user, "Administrator");

            return RedirectToAction(nameof(Admin));
        }

        [HttpGet]
        public async Task<IActionResult> Employee(string searchInput = null, string searchField = null)
        {
            var booksModel = new List<BookModel>();
            switch (searchField)
            {
                case "title":
                    booksModel = await _bookLogic.FindByTitleAsync(searchInput);
                    break;
                case "author":
                    booksModel = await _bookLogic.FindByAuthorAsync(searchInput);
                    break;
                case "genre":
                    booksModel = await _bookLogic.FindByGenreAsync(searchInput);
                    break;
                default:
                    booksModel = await _bookLogic.GetAllBooksAync();
                    break;

            }

            var books = booksModel.Select(b => _mapper.Map<BookViewModel>(b)).ToList();

            var employeeViewModel = new EmployeeViewModel();
            employeeViewModel.Books = books;

            return View(employeeViewModel);
        }

        [HttpPost]
        public IActionResult SearchTitle(EmployeeViewModel employeeViewModel)
        {
            return RedirectToAction(nameof(Employee), "Accounts", new
            {
                searchInput = employeeViewModel.SearchValue,
                searchField = "title"
            });
        }

        [HttpPost]
        public IActionResult SearchAuthor(EmployeeViewModel employeeViewModel)
        {
            return RedirectToAction(nameof(Employee), "Accounts", new
            {
                searchInput = employeeViewModel.SearchValue,
                searchField = "author"
            });
        }

        [HttpPost]
        public IActionResult SearchGenre(EmployeeViewModel employeeViewModel)
        {
            return RedirectToAction(nameof(Employee), "Accounts", new
            {
                searchInput = employeeViewModel.SearchValue,
                searchField = "genre"
            });
        }
    }
}
