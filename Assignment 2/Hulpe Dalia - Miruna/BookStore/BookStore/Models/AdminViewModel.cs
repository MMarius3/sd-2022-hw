using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;

namespace BookStore.Models
{
    public class AdminViewModel
    {
        public List<BookViewModel> Books { get; set; }
        public List<IdentityUser> Users { get; set; }
    }
}
