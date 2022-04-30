using AutoMapper;
using BookStore.BusinessLogic.Models;
using BookStore.Models;
using BookStore.Repository.Models;
using System;
using System.Collections.Generic;
using System.Text;

namespace BookStore.Mappers
{
    public class Profiler: Profile
    {
        public Profiler()
        {
            #region BusinessToView

            CreateMap<BookModel, BookViewModel>();

            #endregion

            #region ViewToBusiness

            CreateMap<BookViewModel, BookModel>();

            #endregion

            #region BusinessToRepo

            CreateMap<BookModel, BookEntity>();

            #endregion

            #region RepoToBusiness

            CreateMap<BookEntity, BookModel>();

            #endregion
        }
    }
}
